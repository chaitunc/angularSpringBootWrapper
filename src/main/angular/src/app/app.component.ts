import { Component } from '@angular/core';
import { AppServiceService } from './app-service.service';
import {Info} from './info';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
 title: Info;
  constructor(private service: AppServiceService){
    service.getTitle().subscribe(res => {
      this.title = res;
    console.log(res);
  },
(err) => {console.log( err);
});
  }


}


