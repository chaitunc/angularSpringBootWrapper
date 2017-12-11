import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Githubinfo } from '../models/githubinfo';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class GithubService {

    constructor(private http: HttpClient) { }

    public getGithubInfo(): Observable<Githubinfo> {
        return this.http.get<Githubinfo>('/api-github/user');
    }
}
