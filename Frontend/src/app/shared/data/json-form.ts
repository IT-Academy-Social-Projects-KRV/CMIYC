import {SearchRequestData} from "./search-request";
import {key} from "ngx-bootstrap-icons";

export class JsonForm {
  name: string = '';
  inputs: JsonFormInput[] = [];
  combinations: ApiCombination[] = [];
}

export class JsonFormInput {
  name: string = '';
  type: FieldType | undefined;
  description: string = '';

  maxLength: number = 0;
  regex: string | undefined;

  min: number | undefined;
  max: number | undefined;

  options: JsonFormInputOption[] = [];
  components: JsonFormInput[] = [];
}

export class JsonFormInputOption {
  text: string = '';
  value: string = '';
}

export enum FieldType {
  text = "text", number = "number", select = "select", object = "object"
}

export class ApiCombination {
  apiName: string = '';
  fields: FieldReference[] = [];

  static isValid(combination: ApiCombination, data: any) {
    const keys = Object.keys(data);
    const fields = combination.fields;
    for (let i = 0; i < fields.length; i++) {
      const ref = fields[i];
      if (ref.required && !keys.includes(ref.field))
        return false;
    }

    return true;
  }

  static buildSearchRequestData(combination: ApiCombination, data: any): SearchRequestData {
    const searchRequestData: SearchRequestData = {};
    const keys = Object.keys(data);

    combination.fields.forEach(field => {
      if(keys.includes(field.field)) {
        searchRequestData[field.field] = data[field.field];
      }
    });

    return searchRequestData;
  }
}

export class FieldReference {
  field: string = '';
  required: boolean = true;
}
