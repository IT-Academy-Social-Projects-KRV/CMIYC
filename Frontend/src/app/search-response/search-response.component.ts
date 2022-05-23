import {Component, HostListener, OnInit} from '@angular/core';
import {RaceCode} from "../shared/data/search.response";
import {HttpClientService} from "../shared/http.client.service";

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
  getScreenHeight: any;
  raceCode: RaceCode = new RaceCode();
  headerViewSize = 227;
  searchResponses: Array<any> | null = null;

  constructor(private http: HttpClientService) {
    this.http.getHistory().subscribe({
      next: value => {
        this.searchResponses = new Array<any>();
        for (let i = 0; i < value.length; i++) {

          if (value[i].api1Responses == null) {
            value[i].api1Responses = new Array<any>();
          }

          if (value[i].api2Responses == null) {
            value[i].api2Responses = new Array<any>();
          }

          if (value[i].api3Responses == null) {
            value[i].api3Responses = new Array<any>();
          }

          this.pushResponsesToSingleArray(value[i].api1Responses, value[i].dateTime);
          this.pushResponsesToSingleArray(value[i].api2Responses, value[i].dateTime);
          this.pushResponsesToSingleArray(value[i].api3Responses, value[i].dateTime);

          if (i == 0 && value[i].api1Responses.length == 0 && value[i].api2Responses.length == 0 && value[i].api3Responses.length == 0) {
            let noRresults: {[key: string]: any} = new Array<any>();
            noRresults["dateTime"] = this.toTwelveHourTimeFormat(value[i].dateTime);
            noRresults["noResult"] = "No results!";
            this.searchResponses.push(noRresults);
          }
        }
      },
      error: err => {
        alert(err.error || err.message || err.description || JSON.stringify(err));
      }
    });
  }

  toTwelveHourTimeFormat(dateTime: string): string {
    return new Date(dateTime).toLocaleString("en-US", {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true
    });
  }

  pushResponsesToSingleArray(apiResponses: Array<any>, dateTime: string): void {
    for (let i = 0; i < apiResponses.length; i++) {
      apiResponses[i]['dateTime'] = this.toTwelveHourTimeFormat(dateTime);
      if (this.searchResponses != null) {
        this.searchResponses.push(apiResponses[i]);
      }
    }
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
