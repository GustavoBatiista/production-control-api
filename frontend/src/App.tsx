import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Layout } from './components/Layout';
import { ProductsPage } from './pages/ProductsPage';
import { RawMaterialsPage } from './pages/RawMaterialsPage';
import { ProductMaterialsPage } from './pages/ProductMaterialsPage';
import { ProducibleProductsPage } from './pages/ProducibleProductsPage';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<h1>Welcome! Choose a menu item above.</h1>} />
          <Route path="products" element={<ProductsPage />} />
          <Route path="raw-materials" element={<RawMaterialsPage />} />
          <Route path="product-materials" element={<ProductMaterialsPage />} />
          <Route path="producible-products" element={<ProducibleProductsPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;