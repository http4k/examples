import React from 'react';
import { render } from '@testing-library/react';
import Http4kReact from './Http4kReact';

test('renders learn react link', () => {
  const { getByText } = render(<Http4kReact />);
  const linkElement = getByText(/Hello/i);
  expect(linkElement).toBeInTheDocument();
});
