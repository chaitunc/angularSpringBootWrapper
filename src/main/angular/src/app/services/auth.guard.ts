import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { TokenService } from './token.service';

@Injectable()
export class AuthGuard implements CanActivate {
 
    constructor(private router: Router, private tokenService: TokenService) { }
 
    canActivate() {
        if (this.tokenService.isUserAuthenticated()) {
            // logged in so return true
            return true;
        }
 
        // not logged in so redirect to login page
        this.router.navigate(['/welcome']);
        return false;
    }
}