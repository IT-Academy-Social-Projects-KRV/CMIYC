import {Collection} from "ngx-pagination/dist/paginate.pipe";

export class SearchResponse {
  api1Responses: Array<Api1> = new Array<Api1>();
  api2Responses: Array<Api2> = new Array<Api2>();
  api3Responses: Array<Api3> = new Array<Api3>();
}

export abstract class ExternalApi {
  apiName: string;
  name: Name;

  protected constructor(apiName: string, name: Name) {
    this.apiName = apiName;
    this.name = name;
  }
}

export class Api1 extends ExternalApi {
  city: string;
  address: string;
  raceCode: string;
  numberOfChildren: number;
  married: boolean;

  constructor(apiName: string, name: Name, city: string, address: string, raceCode: string, numberOfChildren: number, married: boolean) {
    super(apiName, name);
    this.city = city;
    this.address = address;
    this.raceCode = raceCode;
    this.numberOfChildren = numberOfChildren;
    this.married = married;
  }
}

export class Api2 extends ExternalApi {
  carModel: string;
  operatorLicenseExpirationDate: string;
  carModelYear: number;
  carVin: string;
  carNumber: string;
  phone: string;
  image: string;

  constructor(apiName: string, name: Name, carModel: string, operatorLicenseExpirationDate: string, carModelYear: number, carVin: string, carNumber: string, phone: string, image: string) {
    super(apiName, name);
    this.carModel = carModel;
    this.operatorLicenseExpirationDate = operatorLicenseExpirationDate;
    this.carModelYear = carModelYear;
    this.carVin = carVin;
    this.carNumber = carNumber;
    this.phone = phone;
    this.image = image;
  }
}

export class Api3 extends ExternalApi {
  city: string;
  address: string;
  job: string;
  companyName: string;
  phone: string;
  email: string;
  image: string;

  constructor(apiName: string, name: Name, city: string, address: string, job: string, companyName: string, phone: string, email: string, image: string) {
    super(apiName, name);
    this.city = city;
    this.address = address;
    this.job = job;
    this.companyName = companyName;
    this.phone = phone;
    this.email = email;
    this.image = image;
  }
}

export class Name {
  first: string;
  middle: string;
  last: string;
  suffix: string;

  constructor(first: string, middle: string, last: string, suffix: string) {
    this.first = first;
    this.middle = middle;
    this.last = last;
    this.suffix = suffix;
  }
}

export class RaceCode {
  static W = "White";
  static B = "Black or African American";
  static A = "Asian";
  static L = "Latino or Hispanic";
  static I = "American Indian or Alaska Native";
  static H = "Native Hawaiian and Other Pacific Islander";
  static O = "Other";

  getRaceCode(raceCode: string) {
    switch (raceCode) {
      case "W": {
        return RaceCode.W;
      }
      case "B": {
        return RaceCode.B;
      }
      case "A": {
        return RaceCode.A;
      }
      case "L": {
        return RaceCode.L;
      }
      case "I": {
        return RaceCode.I;
      }
      case "H": {
        return RaceCode.H;
      }
      default: {
        return RaceCode.O;
      }
    }
  }
}

