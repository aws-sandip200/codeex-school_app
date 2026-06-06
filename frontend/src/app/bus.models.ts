export interface BusOverview {
  id: number;
  busCode: string;
  plateNumber: string;
  routeName: string;
  driverName: string;
  driverPhone: string;
  status: string;
  currentLatitude: number;
  currentLongitude: number;
  etaMinutes: number;
  delayMinutes: number;
  occupancy: number;
}

export interface StudentJourney {
  studentName: string;
  grade: string;
  pickupStop: string;
  guardianName: string;
  guardianPhone: string;
  morningStatus: string;
  eveningStatus: string;
  lastEventAt: string;
}

export interface NotificationEvent {
  id: number;
  eventType: string;
  title: string;
  message: string;
  channel: string;
  occurredAt: string;
}

export interface Incident {
  id: number;
  busCode: string;
  severity: string;
  status: string;
  description: string;
  resolutionPlan: string;
  createdAt: string;
}

export interface Dashboard {
  bus: BusOverview;
  students: StudentJourney[];
  notifications: NotificationEvent[];
  incidents: Incident[];
}
