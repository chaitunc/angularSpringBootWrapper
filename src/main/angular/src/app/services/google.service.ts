import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Driveinfo } from '../models/driveinfo';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class GoogleService {

    constructor(private http: HttpClient) { }

    public getDriveInfo(): Observable<Driveinfo> {
        return this.http.get<Driveinfo>('user/driveInfo');
    }
}
