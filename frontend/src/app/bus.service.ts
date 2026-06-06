import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { createClient } from '@supabase/supabase-js';
import { Dashboard } from './bus.models';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class BusService {
  private readonly supabase = createClient(environment.supabase.url, environment.supabase.anonKey);

  constructor(private readonly http: HttpClient) {}

  getDashboard(): Observable<Dashboard> {
    return this.http.get<Dashboard>(`${environment.apiBaseUrl}/dashboard/guardian/1`);
  }

  async listRealtimeEvents() {
    const { data, error } = await this.supabase
      .from('notification_events')
      .select('*')
      .order('occurred_at', { ascending: false })
      .limit(10);

    if (error) {
      throw error;
    }

    return data;
  }
}
