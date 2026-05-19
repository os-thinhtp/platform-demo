import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

interface AppLauncher {
  key: string;
  name: string;
  description: string;
  launcherUrl: string;
}

@Component({
  selector: 'app-root',
  imports: [CommonModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  private readonly oauthService = inject(OAuthService);
  private readonly http = inject(HttpClient);

  user: { username: string; authorities: { authority: string }[] } | null = null;
  apps: AppLauncher[] = [];
  roleSummary = '';

  async ngOnInit(): Promise<void> {
    this.oauthService.configure({
      issuer: 'http://localhost:8080/realms/platform-demo',
      clientId: 'platform-ui',
      redirectUri: window.location.origin,
      responseType: 'code',
      scope: 'openid profile email roles',
      showDebugInformation: false
    });

    await this.oauthService.loadDiscoveryDocumentAndTryLogin();

    if (this.oauthService.hasValidAccessToken()) {
      this.loadDashboardData();
    }
  }

  login(): void {
    this.oauthService.initCodeFlow();
  }

  logout(): void {
    this.oauthService.logOut();
    this.user = null;
    this.apps = [];
  }

  private loadDashboardData(): void {
    const headers = {
      Authorization: `Bearer ${this.oauthService.getAccessToken()}`
    };

    this.http.get<{ username: string; authorities: { authority: string }[] }>('http://localhost:8084/api/platform/me', { headers })
      .subscribe((user) => {
        this.user = user;
        this.roleSummary = user.authorities.map((a) => a.authority).join(', ');
      });

    this.http.get<AppLauncher[]>('http://localhost:8084/api/platform/apps', { headers })
      .subscribe((apps) => this.apps = apps);
  }
}
