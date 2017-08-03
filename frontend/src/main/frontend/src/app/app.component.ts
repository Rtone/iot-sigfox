import {Component, OnInit} from "@angular/core";
import "rxjs/add/operator/map";
import "rxjs/add/operator/debounceTime";
import "rxjs/add/operator/filter";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "@angular/common";
import * as moment from "moment";
import {DataModel} from "./model/data.model";
import {DataService} from "./data.service";

@Component({
  selector: 'sigfox-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  lineChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true
        }
      }],
      xAxes: [{
        type: 'linear',
        position: 'bottom'
      }]
    }
  };

  lineChartData: Array<any> = null;

  updateTime: string = '--';

  isGLobal: boolean = false;


  constructor(private route: ActivatedRoute, private location: Location, private dataService: DataService, private router: Router) {
    dataService.messages
      .filter(chartData => chartData.length >= 1)
      .map(sigfoxData => this.transformSigfoxData(sigfoxData))
      .debounceTime(50)
      .subscribe(chartData => this.lineChartData = chartData);
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => this.isGLobal = this.location.path() == '/global');
  }

  grabData(): void {
    this.dataService.getDeviceMessages()
      .filter(chartData => chartData.length >= 1)
      .map(sigfoxData => this.transformSigfoxData(sigfoxData))
      .debounceTime(150)
      .subscribe(chartData => this.lineChartData = chartData);
  }

  private transformSigfoxData(sigfoxData: Array<DataModel>): Array<any> {
    this.lineChartData = null;
    let cpt = 0;
    let datas: Array<any> = sigfoxData.map(entry => {
      this.updateTime = moment(entry.timestamp).fromNow();
      return {x: cpt ++, y: entry.data};
    });
    return [{data: datas, label: sigfoxData[0].name, fill: false}];
  }

}
