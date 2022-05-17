import {Collection} from "ngx-pagination/dist/paginate.pipe";
export declare type ApiResponse = {
  [key: string]: Collection<any>
}
export declare type SearchResponse = {
  [api: string]: ApiResponse
}

