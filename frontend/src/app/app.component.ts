import { CommonModule, DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Dashboard, LoginResponse } from './bus.models';
import { BusService } from './bus.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  dashboard?: Dashboard;
  currentUser?: LoginResponse;
  loading = false;
  error = '';
  credentials = { username: 'parent', password: 'parent123' };
  demoAccounts = [
    { label: 'Student', username: 'student', password: 'student123' },
    { label: 'Parent', username: 'parent', password: 'parent123' },
    { label: 'Driver', username: 'driver', password: 'driver123' },
    { label: 'Admin', username: 'admin', password: 'admin123' }
  ];

  constructor(private readonly busService: BusService) {}

  login(): void {
    this.loading = true;
    this.error = '';

    this.busService.login(this.credentials).subscribe({
      next: (session) => {
        this.currentUser = session;
        this.dashboard = session.dashboard;
        this.loading = false;
      },
      error: () => {
        this.error = 'Login failed. Start the Spring Boot service on port 8080 and use one of the demo accounts.';
        this.loading = false;
      }
    });
  }

  useDemoAccount(account: { username: string; password: string }): void {
    this.credentials = { username: account.username, password: account.password };
  }

  logout(): void {
    this.currentUser = undefined;
    this.dashboard = undefined;
  }

  get statusClass(): string {
    return this.dashboard?.bus.status.toLowerCase().replaceAll('_', '-') ?? 'unknown';
  }
}
