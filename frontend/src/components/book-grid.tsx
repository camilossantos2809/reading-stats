"use client";

import Image from "next/image";
import { AgGridReact, CustomCellRendererProps } from "ag-grid-react";
import {
  AllCommunityModule,
  colorSchemeDarkBlue,
  GetRowIdParams,
  GridOptions,
  ModuleRegistry,
  RowValueChangedEvent,
  themeAlpine,
} from "ag-grid-community";
import { useCallback } from "react";

import { Book } from "@/types";

interface BookGridProps {
  books: Array<Book>;
  onEdit: (book: Book) => Promise<{ message: string }>;
}

ModuleRegistry.registerModules([AllCommunityModule]);

const customTheme = themeAlpine.withPart(colorSchemeDarkBlue).withParams({
  fontFamily: "var(--font-manrope)",
  textColor: "var(--color-foreground)",
  borderColor: "rgba(148, 163, 184, 0.2)",
  rowHoverColor: "rgba(148, 163, 184, 0.1)",
  selectedRowBackgroundColor: "rgba(148, 163, 184, 0.2)",
});

const columnDefs: GridOptions<Book>["columnDefs"] = [
  { field: "id", hide: true },
  {
    headerName: "ISBN",
    field: "isbn",
    flex: 0.5,
  },
  {
    headerName: "Cover",
    field: "isbn",
    cellRenderer: BookCover,
    flex: 0.18,
    editable: false,
    sortable: false,
    filter: false,
  },
  {
    headerName: "Name",
    field: "name",
    flex: 1.8,
  },
  {
    headerName: "Author",
    field: "author",
    flex: 1.25,
  },
  {
    headerName: "Pages",
    field: "pages",
    type: "numericColumn",
    flex: 0.25,
  },
];

const defaultColDef = {
  flex: 1,
  resizable: true,
  sortable: true,
  filter: true,
  editable: true,
};

export default function BookGrid({ books, onEdit }: BookGridProps) {
  const getRowId = useCallback(
    (row: GetRowIdParams<Book>): string => String(row.data.id),
    []
  );

  const onRowValueChanged = useCallback(
    (event: RowValueChangedEvent<Book>) => {
      const data = event.data;
      if (data == null) {
        return;
      }
      onEdit(data);
    },
    [onEdit]
  );

  return (
    <AgGridReact<Book>
      theme={customTheme}
      columnDefs={columnDefs}
      rowData={books}
      defaultColDef={defaultColDef}
      pagination
      paginationPageSize={25}
      getRowId={getRowId}
      onRowValueChanged={onRowValueChanged}
      editType="fullRow"
    />
  );
}

function BookCover(params: CustomCellRendererProps) {
  return (
    <div className="relative rounded-sm overflow-hidden">
      <Image
        alt={`${params.value} cover`}
        src={`https://covers.openlibrary.org/b/isbn/${params.value}-S.jpg`}
        // fill
        className="object-contain"
        width={25}
        height={25}
      />
    </div>
  );
}
