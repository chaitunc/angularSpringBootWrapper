import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user';

@Component({
    selector: 'app-home',
    templateUrl: 'home.component.html'
})
export class HomeComponent implements OnInit {

    userInfo: User;
    constructor(private authSvc: AuthService) { }

    ngOnInit() {
        this.getUser();
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
