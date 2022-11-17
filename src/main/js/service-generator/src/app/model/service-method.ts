import { QueryParameter } from "./query-parameter";

export class ServiceMethod {
    method!: string;
    path!: string;
    returnClass = 'void';
    includeConsumes = true;
    includeProduces = true;
    queryParameters: QueryParameter[] = [];
    postParameterName!: string;
    postParameterType!: string;
}
