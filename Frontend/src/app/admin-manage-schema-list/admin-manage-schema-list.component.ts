import {Component} from '@angular/core';
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
  changingSelectedSchema: boolean = false;

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

  makeSchemaSelected(schema: Schema) {
    if(this.changingSelectedSchema)
      return;

    this.changingSelectedSchema = true;

    const previousSelected: string | undefined = this.schemas.filter(value => value.file.selected)[0]?.file.name;
    this.schemas.forEach(value => value.file.selected = value.file.name == schema.file.name);

    this.httpClientService
      .selectSchema(schema.file.name)
      .subscribe({
        next: res => {
          this.schemas.forEach(value => value.file.selected = value.file.name == schema.file.name);
          this.changingSelectedSchema = false;
        },
        error: err => {
          this.schemas.forEach(value => value.file.selected = value.file.name == previousSelected);
          this.changingSelectedSchema = false;
          alert(err.description || err.message || err);
        }
      });
  }
}
