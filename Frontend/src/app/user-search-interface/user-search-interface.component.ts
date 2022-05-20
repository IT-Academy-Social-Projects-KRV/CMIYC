import {Component} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {JsonForm} from "../shared/data/json-form";

@Component({
  selector: 'app-user-search-interface',
  templateUrl: './user-search-interface.component.html',
  styleUrls: ['./user-search-interface.component.css']
})
export class UserSearchInterfaceComponent {
  jsonForm: JsonForm | undefined;
  formName: string = '';

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService
      .getSelectedSchema()
      .subscribe(response => {
        this.jsonForm = response;
        this.formName =
          this.jsonForm.name.replace(/([A-Z])/g, ' $1');

        console.log((this.jsonForm));
      });
  }
}
