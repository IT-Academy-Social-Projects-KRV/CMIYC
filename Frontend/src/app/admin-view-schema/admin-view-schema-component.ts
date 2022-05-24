import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {Schema, SchemaFile} from "../shared/data/schema";
import {ifStmt} from "@angular/compiler/src/output/output_ast";

@Component({
  selector: 'admin-view-schema',
  templateUrl: './admin-view-schema-component.html',
  styleUrls: ['./admin-view-schema-component.css']
})
export class AdminViewSchemaComponent implements OnChanges {

  xmlSelected: boolean = true;
  currentValue: string = '';
  parseErrors: any = {};

  @Input()
  schema: Schema | undefined = undefined;

  ngOnChanges(changes: SimpleChanges): void {
    if(changes["schema"]) {
      this.loadCurrentValue();
    }
  }

  constructor(private httpClientService: HttpClientService) {}

  loadCurrentValue() {
    if(!this.schema)
      return;

    if(this.xmlSelected && !this.schema.content) {
      this.httpClientService
        .getSchemaContent(this.schema.file.name)
        .subscribe(content => {
          if(this.schema) {
            this.schema.content = content;
            this.currentValue = content;
          }
        });
    } else if(!this.xmlSelected && !this.schema.data) {
      this.httpClientService
        .getSchemaJSON(this.schema.file.name)
        .subscribe({
          next: data => {
            if(this.schema) {
              data = JSON.stringify(data, undefined, 4);
              this.schema.data = data;
              this.currentValue = data;
            }
          },
          error: err => {
            if(this.schema) {
              this.parseErrors[this.schema.file.name] = true;
              this.schema.data = '';
              this.selectXml(true);
            }
          }
        });
    } else {
      const newValue: string = this.xmlSelected ? this.schema.content : this.schema.data;
      this.currentValue = newValue == undefined ? '*not loaded>' : newValue;
    }
  }

  selectXml(xmlSelected: boolean) {
    if(!xmlSelected && this.currentSchemaHasParseError())
      return;

    this.xmlSelected = xmlSelected;
    this.loadCurrentValue();
  }

  getJsonButtonClass(): string {
    if(this.currentSchemaHasParseError()) return 'btn-outline-danger disabled';

    return this.xmlSelected ? 'btn-outline-primary' : 'btn-primary'
  }

  private currentSchemaHasParseError() {
    return this.schema && this.parseErrors[this.schema.file.name];
  }
}
