


# Launch Interceptor Program

#### Determines whether an interceptor should be launched based upon input radar tracking information.

The program determines the boolean values of 15 _Launch Interceptor Conditions_ `LIC` depending on the points on the radar `POINTS` and the relevant parameters `PARAMETERS`.

Then the _Logical Connector Matrix_ `LCM` defines how the output values of the LIC are combined (through logical _or_, _and_ or _not used_).

The _Preliminary Unlocking Vector_ `PUV` determines which `LIC` actually matters in for the launch. An entry with value `true` means that the corresponding `LIC` will be effectively considered.

The output is `true` if and only if the launch should be triggered.

## Inputs


* `NUMPOINTS` The number of planar data points, an `int`.
* `POINTS` An array of at most 100 `java.awt.Point` containing the coordinates of the data points.
* `PARAMETERS` An object holding parameters for LIC’s, more details below, a `Parameters` object.
* `LCM` The Logical Connector Matrix of `LogicalOperators` values (`NOTUSED`, `AND` and `ORR`) . It is a 15x15 array.
* `PUV` The Preliminary Unlocking Vector an array of `boolean`of size 15.

The object `Parameters` is composed of numerous values given in the order used by the constructor.
<details>
<summary><b>Show parameters</b></summary>
</br>
<ul>
<li> <code> double LENGTH1 </code> </li>
<li> <code> double RADIUS1 </code> </li>
<li> <code> double EPSILON </code> </li>
<li> <code> double AREA1 </code> </li>
<li> <code> int QPTS </code> </li>
<li> <code> int QUADS </code> </li>
<li> <code> double DIST </code> </li>
<li> <code> int NPTS </code> </li>
<li> <code> int KPTS </code> </li>
<li> <code> int APTS </code> </li>
<li> <code> int BPTS </code> </li>
<li> <code> int CPTS </code> </li>
<li> <code> int DPTS </code> </li>
<li> <code> int EPTS </code> </li>
<li> <code> int FPTS </code> </li>
<li> <code> int GPTS </code> </li>
<li> <code> double LENGTH2 </code> </li>
<li> <code> double RADIUS2 </code> </li>
<li> <code> double AREA2 </code> </li>
</ul>
</details>

## Output

The program outputs the boolean value `LAUNCH` that determines whether the launch is intercepted or not.

## Statement of contribution

|Emil|Filip |
|:--|:--|
| `CMV` function 2,6,10 and 14 <br/> `FUV` function <br/>Tests 0,3,7 and 11 | Variables & Skeleton <br/> `CMV` function 1,5,9 and 13 <br/> `LAUNCH` function <br/> Tests 2,6,10 and 14 <br/> |
|**Julien** | **Susie** |
`CMV` function 0,4,8 and 12 <br/> `DECIDE` function <br/> Tests 1,5,9 and 13 <br/> Write the README <br/> Test `DECIDE`, `FUV` functions <br/>  | `CMV` function 3,7 and 11 <br/> `PUM` function <br/> Tests 4,8 and 12 <br/>  |
