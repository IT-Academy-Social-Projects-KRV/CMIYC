import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {Schema, SchemaFile} from "../shared/data/schema";

@Component({
  selector: 'app-admin-manage-schema-list',
  templateUrl: './admin-manage-schema-list.component.html',
  styleUrls: ['./admin-manage-schema-list.component.css']
})
export class AdminManageSchemaListComponent {

  schemas: Schema[] = [];
  selectedSchema: number = 0;

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService
      .getSchemas()
      .subscribe(schemas => {
        this.schemas = schemas.map(schemaFile => new Schema(
          new SchemaFile(schemaFile.name, schemaFile.selected, new Date(schemaFile.uploadedAt))
        ));
      });
  }

  deleteSchema(schema: Schema, index: number) {
    if(confirm("Are you sure you want to delete schema\"" + schema.file.name + "\"?\nThis action cannot be undone!")) {
      this.httpClientService
        .deleteSchema(schema.file.name)
        .subscribe(value => {
          this.schemas.splice(index, 1);
          if(index == this.selectedSchema)
            this.selectedSchema = Math.min(this.selectedSchema, this.schemas.length);
        });
    }
  }
}
