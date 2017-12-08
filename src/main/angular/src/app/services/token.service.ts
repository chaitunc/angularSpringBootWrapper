import { Injectable } from '@angular/core';


@Injectable()
export class TokenService {

    constructor() { }

    isUserAuthenticated(): boolean {
        return localStorage.getItem('token') ? true : false;
    }

    public getToken(): string {
        return localStorage.getItem('token');
    }



}
