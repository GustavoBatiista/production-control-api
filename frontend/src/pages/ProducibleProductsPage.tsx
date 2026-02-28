import { useEffect, useState } from 'react';
import { getProducibleProducts } from '../services/api';
import { getErrorMessage } from '../utils/errorMessage';
import type { ProducibleProduct } from '../types';
import { TableContainer } from '../components/TableContainer';

export function ProducibleProductsPage() {
  const [products, setProducts] = useState<ProducibleProduct[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getProducibleProducts();
      setProducts(res.data);
    } catch (e) {
      setError(getErrorMessage(e));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  return (
    <div>
      <h1 className="page-title">Producible Products</h1>
      <p className="empty-hint">
        Products that can be produced based on current raw material stock, ordered by price (lowest first).
      </p>
      <button className="btn btn-secondary" onClick={load} disabled={loading}>Refresh</button>
      {error && <div className="error-msg">{error}</div>}
      {loading ? (
        <p className="loading">Loading...</p>
      ) : (
        <TableContainer>
        <table className="data-table">
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Price</th>
              <th>Max Quantity</th>
            </tr>
          </thead>
          <tbody>
            {products.length === 0 ? (
              <tr>
                <td colSpan={4} style={{ padding: 24, textAlign: 'center', color: '#666' }}>
                  No producible products. Add products, raw materials, and associations first.
                </td>
              </tr>
            ) : (
              products.map((p) => (
                <tr key={p.productId}>
                  <td>{p.productCode}</td>
                  <td>{p.productName}</td>
                  <td>{p.price}</td>
                  <td>{p.maxQuantity}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
        </TableContainer>
      )}
    </div>
  );
}