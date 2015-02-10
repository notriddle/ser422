package edu.asupoly.cst533;

import java.io.File;

import edu.asupoly.cst533.Cst533DbWrapperException;

public interface IXRayService {
    public boolean readXRayImage(int id, File outfile) throws Cst533DbWrapperException;
    public boolean readXRayDiagnosis(int id, File outfile) throws Cst533DbWrapperException;

    public boolean writeXRayImage(int id, File file) throws Cst533DbWrapperException;
    public boolean writeXRayDiagnosis(int id, File file) throws Cst533DbWrapperException;
}
