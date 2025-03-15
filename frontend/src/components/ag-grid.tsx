"use client";

import { AgGridReact } from "ag-grid-react";
import {
  AllCommunityModule,
  colorSchemeDarkBlue,
  ModuleRegistry,
  themeAlpine,
} from "ag-grid-community";

interface BookGridProps {
  books: Array<{
    isbn: string;
    name: string;
    author: string | null;
    pages: number | null;
  }>;
}

ModuleRegistry.registerModules([AllCommunityModule]);

const customTheme = themeAlpine.withPart(colorSchemeDarkBlue).withParams({
  fontFamily: "var(--font-manrope)",
  textColor: "var(--color-foreground)",
  borderColor: "rgba(148, 163, 184, 0.2)",
  rowHoverColor: "rgba(148, 163, 184, 0.1)",
  selectedRowBackgroundColor: "rgba(148, 163, 184, 0.2)",
});

const columnDefs = [
  {
    headerName: "ISBN",
    field: "isbn",
    sortable: true,
    filter: true,
    cellClass: "font-medium",
  },
  {
    headerName: "Name",
    field: "name",
    sortable: true,
    filter: true,
    flex: 2,
    cellClass: "font-medium",
  },
  {
    headerName: "Author",
    field: "author",
    sortable: true,
    filter: true,
    flex: 1.5,
  },
  {
    headerName: "Pages",
    field: "pages",
    sortable: true,
    filter: true,
    width: 120,
    type: "numericColumn",
  },
];

const defaultColDef = {
  flex: 1,
  minWidth: 100,
  resizable: true,
  sortable: true,
  filter: true,
};

export default function BookGrid({ books }: BookGridProps) {
  return (
    <AgGridReact
      theme={customTheme}
      columnDefs={columnDefs}
      rowData={books}
      defaultColDef={defaultColDef}
      pagination={true}
      paginationPageSize={25}
    />
  );
}
