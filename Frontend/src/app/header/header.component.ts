import { isNull } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { repos } from '../repos';
import { Router } from "@angular/router";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

constructor( private authService: AuthService){}
  ngOnInit(): void {
  }
  isLogout() {
    console.log("logout work");
    this.authService.logout();
    }
}
