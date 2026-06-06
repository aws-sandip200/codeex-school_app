import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Dashboard } from './bus.models';
import { BusService } from './bus.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  dashboard?: Dashboard;
  loading = true;
  error = '';

  constructor(private readonly busService: BusService) {}

  ngOnInit(): void {
    this.busService.getDashboard().subscribe({
      next: (dashboard) => {
        this.dashboard = dashboard;
        this.loading = false;
      },
      error: () => {
        this.error = 'Live API is offline. Showing the product shell; start the Spring Boot service on port 8080.';
        this.loading = false;
      }
    });
  }

  get statusClass(): string {
    return this.dashboard?.bus.status.toLowerCase().replaceAll('_', '-') ?? 'unknown';
  }
}
