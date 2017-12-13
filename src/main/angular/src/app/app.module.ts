import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule  } from './app-routing.module';
import { AppComponent } from './app.component';
import { DriveComponent } from './drive/drive.component';
import { GithubComponent } from './github/github.component';
import { NavbarComponent } from './navbar/navbar.component';

import { AuthService } from './services/auth.service';
import { TokenService } from './services/token.service';
import { AuthGuard } from './services/auth.guard';
import { HomeComponent } from './home/home.component';
import { GoogleService } from './services/google.service';
import { GithubService } from './services/github.service';
import { WelcomeComponent } from './welcome/welcome.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './services/jwt.intercetor';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    WelcomeComponent,
    DriveComponent,
    GithubComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [  AuthService, GoogleService, GithubService, AuthGuard, TokenService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
      deps: [TokenService]
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
