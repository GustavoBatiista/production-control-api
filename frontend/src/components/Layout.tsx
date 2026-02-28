import { Link, Outlet, useLocation } from 'react-router-dom';

export function Layout() {
  const location = useLocation();

  return (
    <div style={{ padding: 20, maxWidth: 1200, margin: '0 auto' }}>
      <nav className="page-nav">
        <Link to="/products" className={location.pathname === '/products' ? 'active' : ''}>Products</Link>
        <Link to="/raw-materials" className={location.pathname === '/raw-materials' ? 'active' : ''}>Raw Materials</Link>
        <Link to="/product-materials" className={location.pathname === '/product-materials' ? 'active' : ''}>Associations</Link>
        <Link to="/producible-products" className={location.pathname === '/producible-products' ? 'active' : ''}>Producible Products</Link>
      </nav>
      <main>
        <Outlet />
      </main>
    </div>
  );
}