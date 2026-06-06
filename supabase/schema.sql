-- Supabase schema for SkoolBus Guardian.
-- Run this in the Supabase SQL editor, then point Spring Boot to the project Postgres connection string.

create table if not exists buses (
  id bigserial primary key,
  bus_code text not null unique,
  plate_number text not null,
  route_name text not null,
  driver_name text not null,
  driver_phone text not null,
  status text not null default 'ON_ROUTE',
  current_latitude double precision not null,
  current_longitude double precision not null,
  eta_minutes integer not null default 0,
  delay_minutes integer not null default 0,
  occupancy integer not null default 0,
  created_at timestamptz not null default now()
);

create table if not exists students (
  id bigserial primary key,
  student_name text not null,
  grade text not null,
  pickup_stop text not null,
  guardian_name text not null,
  guardian_phone text not null,
  morning_status text not null,
  evening_status text not null,
  last_event_at timestamptz,
  bus_id bigint not null references buses(id) on delete cascade
);

create table if not exists notification_events (
  id bigserial primary key,
  event_type text not null,
  title text not null,
  message text not null,
  channel text not null,
  occurred_at timestamptz not null,
  bus_id bigint not null references buses(id) on delete cascade
);

create table if not exists incidents (
  id bigserial primary key,
  severity text not null,
  status text not null,
  description text not null,
  resolution_plan text not null,
  created_at timestamptz not null default now(),
  bus_id bigint not null references buses(id) on delete cascade
);

alter table buses enable row level security;
alter table students enable row level security;
alter table notification_events enable row level security;
alter table incidents enable row level security;

create policy "guardians can read bus data" on buses for select using (true);
create policy "guardians can read assigned students" on students for select using (true);
create policy "guardians can read notifications" on notification_events for select using (true);
create policy "guardians can read incidents" on incidents for select using (true);

insert into buses (bus_code, plate_number, route_name, driver_name, driver_phone, status, current_latitude, current_longitude, eta_minutes, delay_minutes, occupancy)
values ('G071845', 'KA-05-MSB-1845', 'East City Route - Sector 14', 'Ravi Kumar', '+91 98765 43210', 'DELAYED', 12.9716, 77.5946, 2, 4, 28)
on conflict (bus_code) do nothing;

insert into students (student_name, grade, pickup_stop, guardian_name, guardian_phone, morning_status, evening_status, last_event_at, bus_id)
select 'Aarav Sharma', 'Grade 4', 'Maple Apartments', 'Neha Sharma', '+91 90000 10001', 'PICKED_UP_FROM_STOP', 'WAITING_AT_SCHOOL', now(), id from buses where bus_code = 'G071845'
on conflict do nothing;

insert into notification_events (event_type, title, message, channel, occurred_at, bus_id)
select event_type, title, message, channel, occurred_at::timestamptz, b.id
from buses b,
(values
  ('DEPARTURE', 'Bus departed', 'Good Morning. The bus G071845 has departed from school and will reach your stop on time.', 'PUSH', '2026-06-06T08:00:00Z'),
  ('DELAY', 'Traffic delay', 'The bus G071845 would be late by 4 minutes due to heavy traffic.', 'PUSH + SMS', '2026-06-06T08:14:00Z'),
  ('NEAR_STOP', 'Two minutes away', 'Bus G071845 will reach your stop within 2 minutes.', 'PUSH', '2026-06-06T08:28:00Z'),
  ('NFC_SCAN', 'Child boarded', 'Your child has been safely picked up from the bus stop.', 'PUSH', '2026-06-06T08:50:00Z'),
  ('INCIDENT', 'Alternate bus assigned', 'The alternative bus G633393 is on the way and will be arriving soon at your bus stop.', 'PUSH + CALL', '2026-06-06T17:10:00Z')
) as seed(event_type, title, message, channel, occurred_at)
where b.bus_code = 'G071845';
