import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {Schema, SchemaFile} from "../shared/data/schema";

@Component({
  selector: 'app-admin-manage-schema-list',
  templateUrl: './admin-manage-schema-list.component.html',
  styleUrls: ['./admin-manage-schema-list.component.css']
})
export class AdminManageSchemaListComponent implements OnInit {

  schemas: Schema[] = [];

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService
      .getSchemas()
      .subscribe(schemas => {
        this.schemas = schemas.map(schemaFile => new Schema(
          new SchemaFile(schemaFile.name, schemaFile.selected, new Date(schemaFile.uploadedAt))
        ));
      });
  }

  ngOnInit(): void {

  }
}
