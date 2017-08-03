import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {DataService} from "./data.service";
import {WebsocketService} from "./websocket.service";
import {ChartsModule} from "ng2-charts";
import {RouterModule} from "@angular/router";
import {ROUTES} from "./app.routes";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ChartsModule,
    RouterModule.forRoot(ROUTES)
  ],
  providers: [DataService, WebsocketService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
