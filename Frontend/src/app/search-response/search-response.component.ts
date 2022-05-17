import { Component, OnInit } from '@angular/core';
import {RESPONSES} from "../shared/Mock/Repo";
import {ResponseService} from "../shared/response.service";
import {SearchResponse} from "../shared/data/search.response";

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

  constructor(private responseService: ResponseService) {
    this.searchResponses = this.responseService.response as SearchResponse;
  }

  ngOnInit(): void {
    if (this.responseService.response !== undefined) {
      console.log(this.searchResponses);
    }
  }

  onTableDataChange(event:any){
    this.page = event;

  }
/*  onTableSizeChange(event:any): void {
    this.tableSize = event.target.value;
    this.page = 1;
  }*/

}
