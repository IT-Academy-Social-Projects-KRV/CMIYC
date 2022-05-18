import {Component, HostListener, OnInit} from '@angular/core';
import {ResponseService} from "../shared/response.service";
import {RaceCode, SearchResponse} from "../shared/data/search.response";

@Component({
  selector: 'app-search-response',
  templateUrl: './search-response.component.html',
  styleUrls: ['./search-response.component.css']
})
export class SearchResponseComponent implements OnInit {

  POSTS: any;
  page = 1;
  count: number | undefined;
  tableSize = 5;
  searchResponses: SearchResponse;
  getScreenWidth: any;
  getScreenHeight: any;
  raceCode: RaceCode = new RaceCode();

  constructor(private responseService: ResponseService) {
    this.searchResponses = {
      "api1Responses": [
        {
          "apiName":"API1",
          "city":"Topeka",
          "address":"09 Mesta Alley",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":"Doctor"
          },
          "raceCode":"L",
          "numberOfChildren":0,
          "married":false
        },
        {
          "apiName":"API1",
          "city":"Topeka",
          "address":"09 Mesta Alley",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":""
          },
          "raceCode":"L",
          "numberOfChildren":0,
          "married":false
        },
        {
          "apiName": "API1",
          "name":{
            "first":"Michelle",
            "middle":"",
            "last":"Martinez",
            "suffix":""
          },
          "city": "Orlando",
          "address": "8 3rd Avenue",
          "raceCode": "L",
          "numberOfChildren":0,
          "married":false
        },
      ],
      "api2Responses": [
        {
          "apiName":"API2",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":""
          },
          "carModel":"Navajo",
          "operatorLicenseExpirationDate":"09.08.2025",
          "carModelYear":1994,
          "carVin":"2G4GU5GV9C9958996",
          "carNumber":"5L3•EXO",
          "phone":"785-263-8114",
          "image":"https://avatars.dicebear.com/api/female/4269.png"
        },
        {
          "apiName":"API2",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":""
          },
          "carModel":"Navajo",
          "operatorLicenseExpirationDate":"09.08.2025",
          "carModelYear":1994,
          "carVin":"2G4GU5GV9C9958996",
          "carNumber":"5L3•EXO",
          "phone":"785-263-8114",
          "image":"https://avatars.dicebear.com/api/female/4269.png"
        }
      ],
      "api3Responses": [
        {
          "apiName":"API3",
          "city":"Topeka",
          "address":"09 Mesta Alley",
          "job":"Staff Accountant III",
          "companyName":"McLaughlin, Schimmel and Raynor",
          "phone":"785-263-8114",
          "email":"jdensumbe0@dagondesign.com",
          "image":"https://avatars.dicebear.com/api/female/4269.png",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":""
          }
        },
        {
          "apiName":"API3",
          "city":"Topeka",
          "address":"09 Mesta Alley",
          "job":"Staff Accountant III",
          "companyName":"McLaughlin, Schimmel and Raynor",
          "phone":"785-263-8114",
          "email":"jdensumbe0@dagondesign.com",
          "image":"https://avatars.dicebear.com/api/female/4269.png",
          "name":{
            "first":"Donna",
            "middle":"Jan",
            "last":"Davis",
            "suffix":""
          }
        }
      ]
    };
  }

  ngOnInit(): void {
    /*if (this.responseService.response !== undefined) {
      console.log(this.searchResponses);
    }*/
    console.log(this.searchResponses);
    this.getScreenWidth = window.innerWidth;
    this.getScreenHeight = window.innerHeight - 227;
    console.log(this.getScreenHeight);
  }

  onTableDataChange(event:any){
    this.page = event;

  }
/*  onTableSizeChange(event:any): void {
    this.tableSize = event.target.value;
    this.page = 1;
  }*/

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.getScreenWidth = window.innerWidth;
    this.getScreenHeight = window.innerHeight - 227;
  }
}
