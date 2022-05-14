import { Component, OnInit } from '@angular/core';
import {RESPONSES} from "../shared/Mock/Repo";

@Component({
  selector: 'app-search-response',
  templateUrl: './search-response.component.html',
  styleUrls: ['./search-response.component.css']
})
export class SearchResponseComponent implements OnInit {

  POSTS: any;
  page = 1;
  count:number|undefined;
  tableSize = 5;
  responses = RESPONSES;

  constructor() { }

  ngOnInit(): void {
    this.count=this.responses.length;
  }

  onTableDataChange(event:any){
    this.page = event;

  }
/*  onTableSizeChange(event:any): void {
    this.tableSize = event.target.value;
    this.page = 1;
  }*/

}
