import { DataGrid } from "@mui/x-data-grid";

export function DataGridComponent(props) {
    console.log(props.rows)
    console.log(props.columns)
  return (
    <DataGrid
      rows={props.rows}
      columns={props.columns}
      getRowId={(row) => row[props.rowId]}
    />
  );
}
