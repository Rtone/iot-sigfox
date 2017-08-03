import {Injectable} from "@angular/core";
import {WebsocketService} from "./websocket.service";
import {Subject} from "rxjs/Subject";
import "rxjs/add/operator/map";
import {DataModel} from "./model/data.model";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";


const WS_URL = '/data';
const HTTP_URL = '/api/messages';
const HTTP_DEVICE_URL = '/api/device/messages';

@Injectable()
export class DataService {

  public messages: Subject<Array<DataModel>>;

  constructor(wsService: WebsocketService, private  http: Http) {
    let wsUrl: string = window.location.origin.concat(WS_URL).replace('http', 'ws').replace('4200', '8080');
    this.messages = <Subject<Array<DataModel>>>wsService.connect(wsUrl)
      .map(message => JSON.parse(message.data));
  }

  getMessages(): Observable<Array<DataModel>> {
    return this.http.get(HTTP_URL)
      .map(this.extractData);
  }

  getDeviceMessages(): Observable<Array<DataModel>> {
    return this.http.get(HTTP_DEVICE_URL)
      .map(this.extractData);
  }

  private extractData(res: Response): Array<DataModel> {
    let body = res.json();
    return body as Array<DataModel> || [];
  }

}
