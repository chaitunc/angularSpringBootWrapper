import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { User } from '../models/user';

@Injectable()
export class AuthService {

    constructor(private http: HttpClient) { }

    isUserAuthenticated(): boolean {
        return localStorage.getItem('token') ? true : false;
    }

    public getToken(): string {
        return localStorage.getItem('token');
    }

    public getUser(): Observable<User> {
        return Observable.create(observer => {
            this.http.get<User>('/pdb-gateway/user').subscribe(
                (res) => {
                    if ( res.details ) {
                        localStorage.setItem('token', res.details.tokenValue);
                    }
                    observer.next(res);
                    observer.complete();
                },
                (err) => {
                    console.log(err);
                    observer.next(null);
                    observer.complete();
                }
            );
        });

    }

    


}
