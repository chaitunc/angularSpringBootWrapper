import { Component, OnInit } from '@angular/core';
import { AuthService  } from '../services/auth.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { Router } from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html'
})
export class NavbarComponent implements OnInit {

    constructor(private authSvc: AuthService, private router: Router) { }

    ngOnInit() {
        this.authSvc.getUser().subscribe(
            (resp) => {
                console.log(resp);
                this.isAuthenticated().subscribe(
                    (res) => {
                        if(res){
                            this.router.navigate(['user']);
                        }
                    });
            },
            (error) => {
                console.log(error);
            }
        );
    }

    public isAuthenticated(): Observable<boolean> {
       return  Observable.of(this.authSvc.isUserAuthenticated());
    }

}
