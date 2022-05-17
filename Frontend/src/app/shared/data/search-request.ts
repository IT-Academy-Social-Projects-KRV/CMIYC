export class SearchRequest {

  apis: string[] = [];
  data: any = {};

  constructor(apis: string[], data: any) {
    this.apis = apis;
    this.data = data;
  }
}
