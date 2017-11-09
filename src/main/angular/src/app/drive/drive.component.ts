import { Component, OnInit } from '@angular/core';
import { GoogleService } from '../services/google.service';
import { Driveinfo } from '../models/driveinfo';

@Component({
    selector: 'app-drive',
    templateUrl: 'drive.component.html'
})
export class DriveComponent implements OnInit {

    driveInfo: Driveinfo;
    constructor(private googleSvc: GoogleService) { }

    ngOnInit() {
        this.googleSvc.getDriveInfo().subscribe(
            (res) => {
                this.driveInfo = res;
            },
            (err) => {
                console.log(err);
            }
        );
    }
}
