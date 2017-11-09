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

    public authenticate(): Observable<boolean> {

        return Observable.create(observer => {
            this.http.get('login', {
                headers: new HttpHeaders(
                    {'Access-Control-Allow-Origin' : '*',
                     'Access-Control-Allow-Methods': 'POST, GET'
                    }) }).subscribe(
                (res) => {
                    observer.next(true);
                    observer.complete();
                },
                (err) => {
                    console.log(err);
                    observer.next(true);
                    observer.complete();
                }
            );
        });

    }

    public getUser(): Observable<User> {
        return Observable.create(observer => {
            this.http.get<User>('user', {
                 headers: new HttpHeaders(
                     {'Access-Control-Allow-Origin' : '*',
                     'Access-Control-Allow-Methods': 'POST, GET'
                    }) }).subscribe(
                (res) => {
                    localStorage.setItem('token', res.details.tokenValue);
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
