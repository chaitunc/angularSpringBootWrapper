import {Component, OnInit, ChangeDetectorRef, OnDestroy} from '@angular/core';
import { GithubService } from '../services/github.service';
import { Githubinfo } from '../models/githubinfo';

@Component({
    selector: 'app-github',
    templateUrl: 'github.component.html'
})
export class GithubComponent implements OnInit {

    githubInfo: Githubinfo;
    
    constructor(private cdRef: ChangeDetectorRef,
        private githubSvc: GithubService) {
        this.githubInfo = { login: 'test', url: 'test' };
     }

    ngOnInit() {
        this.githubSvc.getGithubInfo().subscribe(
            (res) => {
                this.githubInfo = res;
                console.log(this.githubInfo);
                this.cdRef.detectChanges();
            },
            (err) => {
                console.log(err);
            }
        );
    }
}
