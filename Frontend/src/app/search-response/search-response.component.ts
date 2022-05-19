import {Component, HostListener, OnInit} from '@angular/core';
import {ResponseService} from "../shared/response.service";
import {RaceCode, SearchResponse} from "../shared/data/search.response";
import {count} from "rxjs";

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
  getScreenHeight: any;
  raceCode: RaceCode = new RaceCode();
  headerViewSize = 227;

  constructor(private responseService: ResponseService) {
    this.searchResponses = responseService.response;
  }

  ngOnInit(): void {
    this.onWindowResize();
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
    this.getScreenHeight = window.innerHeight - this.headerViewSize;
    this.tableSize = (this.getScreenHeight - 307) / 52;
    if (this.tableSize <= 1) {
      this.tableSize = 1;
    }
  }
}
