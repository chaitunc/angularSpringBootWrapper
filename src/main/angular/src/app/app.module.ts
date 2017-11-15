import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule  } from './app-routing.module';
import { AppComponent } from './app.component';
import { DriveComponent } from './drive/drive.component';
import { NavbarComponent } from './navbar/navbar.component';

import { AuthService } from './services/auth.service';
import { HomeComponent } from './home/home.component';
import { GoogleService } from './services/google.service';
import { WelcomeComponent } from './welcome/welcome.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './services/jwt.intercetor';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    WelcomeComponent,
    DriveComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [  AuthService, GoogleService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
