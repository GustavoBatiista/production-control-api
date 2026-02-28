type TableContainerProps = {
    children: React.ReactNode;
  };
  
  export function TableContainer({ children }: TableContainerProps) {
    return <div className="table-container">{children}</div>;
  }