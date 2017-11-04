import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {Observable} from 'rxjs/Observable';
import {Info} from './info';

@Injectable()
export class AppServiceService {
private title = 'default';
  constructor(private http: HttpClient) {

   }
getTitle(): Observable<Info> {
// Make the HTTP request:
  return this.http.get<Info>(environment.serviceURL);
}

}
