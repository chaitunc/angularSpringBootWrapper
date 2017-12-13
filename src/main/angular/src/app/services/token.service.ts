import { Injectable } from '@angular/core';
import { tokenNotExpired } from 'angular2-jwt';

@Injectable()
export class TokenService {

    constructor() { }

    isUserAuthenticated(): boolean {
        return tokenNotExpired('token');
    }

    public getToken(): string {
        return localStorage.getItem('token');
    }

}
