import { Component, OnInit } from '@angular/core';
import { AuthService  } from '../services/auth.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html'
})
export class NavbarComponent implements OnInit {



    constructor(private authSvc: AuthService) { }

    ngOnInit() {
        this.isAuthenticated();
    }

    public isAuthenticated(): Observable<boolean> {
       return  Observable.of(this.authSvc.isUserAuthenticated());
    }

}
