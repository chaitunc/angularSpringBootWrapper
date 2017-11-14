import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user';

@Component({
    selector: 'app-login',
    templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

    userInfo: User;
    constructor(private authSvc: AuthService) { }

    ngOnInit() {

    }

    public getUser(): void {
        this.authSvc.getUser().subscribe(
            (resp) => {
                this.userInfo = resp;
            },
            (error) => {
                console.log(error);
            }
        );
    }


}
