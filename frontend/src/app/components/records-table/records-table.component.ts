import { Record } from './../../models/Record';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-records-table',
  templateUrl: './records-table.component.html',
  styleUrls: ['./records-table.component.sass']
})
export class RecordsTableComponent implements OnInit {

  @Input() recordsList: Record[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  getRecordType(isGeneration: boolean): string {
    if(isGeneration) {
      return "GERACAO";
    } else {
      return "COMPRA";
    }
  }

}
