import React, { createContext, useState } from 'react';

const ComponentContext = createContext();

export const ComponentProvider = ({ children }) => {
  const [showPopUp, setShowPopUp] = useState(false);

  return (
    <ComponentContext.Provider value={{ showPopUp, setShowPopUp }}>
      {children}
    </ComponentContext.Provider>
  );
};

export const useComponentContext = () => {
  return React.useContext(ComponentContext);
};
