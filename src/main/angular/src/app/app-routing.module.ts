import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent  } from './home/home.component';
import {  WelcomeComponent } from './welcome/welcome.component';

const appRoutes: Routes = [
    { path: '', component : WelcomeComponent, pathMatch: 'full' },
    { path: 'home', component : HomeComponent, pathMatch: 'full' },
    { path: 'logout', redirectTo: '' , pathMatch: 'full' }
];

@NgModule({
    imports: [
      RouterModule.forRoot(
        appRoutes,
        {
          enableTracing: true, // <-- debugging purposes only
         }
      )
    ],
    exports: [
      RouterModule
    ],
    providers: [

    ]
  })
  export class AppRoutingModule {
  }
