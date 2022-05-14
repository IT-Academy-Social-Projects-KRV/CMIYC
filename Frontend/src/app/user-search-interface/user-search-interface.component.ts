import {Component} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {InterfaceSchema} from "../shared/data/interface-schema";

@Component({
  selector: 'app-user-search-interface',
  templateUrl: './user-search-interface.component.html',
  styleUrls: ['./user-search-interface.component.css']
})
export class UserSearchInterfaceComponent {
  schema: InterfaceSchema | undefined;
  normalizedTransactionName: string = '';

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService
      .getSelectedSchema()
      .subscribe(response => {
        this.schema = response;
        this.normalizedTransactionName =
          this.schema.transaction.name.replace(/([A-Z])/g, ' $1');

        console.log((this.schema));
      });
  }
}
