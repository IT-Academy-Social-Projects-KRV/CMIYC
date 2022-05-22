export class SearchResponse {
  api1Responses: Array<any> = new Array<any>();
  api2Responses: Array<any> = new Array<any>();
  api3Responses: Array<any> = new Array<any>();
  dateTime: string = "";
}

export class RaceCode {
  getRace: {[key: string]: any} = {
    "W": "White",
    "B": "Black or African American",
    "A": "Asian",
    "L": "Latino or Hispanic",
    "I": "American Indian or Alaska Native",
    "H": "Native Hawaiian and Other Pacific Islander",
    "O": "Other"
  }
}

